package com.personal.dropbox.client.console;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.google.gson.Gson;
import com.personal.dropbox.client.model.FileMetaData;
import com.personal.dropbox.client.service.DropboxService;
import com.personal.dropbox.client.util.SizeConverter;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DropboxConsole {

  private final DropboxService dropboxService;
  private final Scanner scanner;
  private final SizeConverter sizeConverter;
  private final Gson gson;

  public void init(String[] args) {
    if (args.length > 4) {
      System.out.println("Hint: Arguments cannot be more than four");
    }
    switch (args[0]) {
      case "auth":
        authHandler(args[1], args[2]);
        break;
      case "list":
        listInfoHandler(args[1], args[2]);
        break;
      case "info":
        userInfoHandler(args[1]);
        break;
      default:
        System.out.println("Invalid or Operation not supported yet: " + args[0]);
        System.out.println("Supported operations are \"auth\" \"list\" \"info\" ");
    }
  }

  private void authHandler(String key, String secret) {
    log.debug("Generating web request for key {}", key);
    DbxWebAuth webAuth = dropboxService.buildWebAuthRequest(key, secret);
    String authorizeUrl = webAuth.authorize(DbxWebAuth.newRequestBuilder()
                                                      .withNoRedirect()
                                                      .build());
    System.out.println("1. Go to: " + authorizeUrl);
    System.out.println("2. Click \"Allow\" (you might have to log in first)");
    System.out.println("3. Copy the authorization code and paste it here:");
    String code = scanner.next();
    if (code == null) {
      System.exit(1);
      return;
    }
    code = code.trim();
    DbxAuthFinish authFinish;
    try {
      authFinish = webAuth.finishFromCode(code);
    } catch (
        DbxException ex) {
      System.err.println("Error in DbxWebAuth.authorize: " + ex.getMessage());
      System.exit(1);
      return;
    }
    System.out.println("User id " + authFinish.getUserId());
    System.out.println("Your access token:\n" + authFinish.getAccessToken());
  }

  private void userInfoHandler(String accessToken) {
    Optional<FullAccount> fullAccount = dropboxService.getUserInfo(accessToken);
    if (fullAccount.isPresent()) {
      FullAccount account = fullAccount.get();
      System.out.println("-----------------------------------------------------");
      System.out.println("Account ID: \t\t" + account.getAccountId());
      System.out.println("Display Name: \t" + account.getName()
                                                     .getDisplayName());
      System.out.println("Name: \t\t\t\t\t" + account.getName()
                                                     .getGivenName() + " " + account.getName()
                                                                                    .getSurname()
          + " ("
          + account.getName()
                   .getFamiliarName() + ")");
      System.out.println(
          "Email: \t\t\t\t\t" + account.getEmail() + getEmailStatus(account.getEmailVerified()));
      System.out.println("Country: \t\t\t\t" + account.getCountry());
      System.out.println("Referral link:  " + account.getReferralLink());
      System.out.println("-----------------------------------------------------");
    } else {
      System.out.println("Unable to get user info");
    }
  }

  private String getEmailStatus(boolean emailStatus) {
    return emailStatus ? " (Verified)" : " (Not Verified)";
  }

  private void listInfoHandler(String accessToken, String path) {
    if (path.contains(".")) {
      fileInfoHandler(accessToken, path);
    } else {
      folderInfoHandler(accessToken, path);
    }
  }

  private void folderInfoHandler(String accessToken, String path) {
    Optional<ListFolderResult> listFolderResult = dropboxService.getFolderDetails(accessToken,
        path);
    System.out.println("-----------------------------------------------------");
    System.out.println(path + " \t\t\t\t\t\t : dir");
    listFolderResult.ifPresentOrElse(listFolder -> displayDirectoryInfo(path, listFolder),
        () -> System.out.println("Unable to get directory info " + path));
    System.out.println("-----------------------------------------------------");

  }

  private void fileInfoHandler(String accessToken, String path) {
    Optional<Metadata> fileDetails = dropboxService.getFileDetails(accessToken, path);
    System.out.print(path);
    fileDetails.ifPresentOrElse(fileDetail -> printFileMetaData(path, fileDetail),
        () -> System.out.println("Unable to get file info for path " + path));

  }

  private void displayDirectoryInfo(String path, ListFolderResult listFolderResult) {
    listFolderResult.getEntries()
                    .forEach(entry -> printMetadata(path, entry));
  }

  private void printMetadata(String path, Metadata metadata) {
    if (metadata instanceof FileMetadata) {
      printFileMetaData(path, metadata);
    } else {
      System.out.println(metadata.getPathLower()
                                 .substring(path.length()) + ":\t\t" + ": dir");
    }
  }

  private void printFileMetaData(String path, Metadata metadata) {
    FileMetaData fileMetaData = gson.fromJson(metadata.toString(), FileMetaData.class);
    System.out.println(metadata.getPathLower()
                               .substring(path.length()) + ":\t\t" + ": " + fileMetaData.getTag() +
        ", " + getSize(fileMetaData.getSize()) + ", modified at " +
        fileMetaData.getServerModifiedDate()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
  }

  private String getSize(long size) {
    return sizeConverter.getString(size);
  }
}


