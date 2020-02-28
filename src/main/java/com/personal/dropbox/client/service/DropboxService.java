package com.personal.dropbox.client.service;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DropboxService {

  private static final String CLIENT_IDENTIFIER = "personal-client";

  public DbxWebAuth buildWebAuthRequest(String key, String secret) {
    DbxAppInfo appInfo = new DbxAppInfo(key, secret);
    DbxRequestConfig requestConfig = new DbxRequestConfig(CLIENT_IDENTIFIER);
    return new DbxWebAuth(requestConfig, appInfo);
  }

  public Optional<FullAccount> getUserInfo(String accessToken) {
    try {
      FullAccount currentUser = getDbxClientV2(accessToken).users()
                                                           .getCurrentAccount();
      return Optional.of(currentUser);
    } catch (DbxException e) {
      log.debug("Error occurred while fetching the current user info due do {}" , e);
      return Optional.empty();
    }
  }

  public Optional<ListFolderResult> getFolderDetails(String accessToken, String path) {
    try {
      return Optional.of(getDbxClientV2(accessToken).files()
                                                    .listFolder(path));
    } catch (DbxException e) {
      log.debug("Error occurred while fetching the folder details for the path {} due to  {}", path,
          e);
      return Optional.empty();
    }
  }

  public Optional<Metadata> getFileDetails(String accessToken, String path) {
    try {
      return Optional.of(getDbxClientV2(accessToken).files()
                                                    .getMetadata(path));
    } catch (DbxException e) {
      log.debug("Error occurred while fetching the files details from path {} due to " + e, path);
      return Optional.empty();
    }
  }

  DbxClientV2 getDbxClientV2(String accessToken) {
    DbxRequestConfig config = DbxRequestConfig.newBuilder(CLIENT_IDENTIFIER)
                                              .build();
    return new DbxClientV2(config, accessToken);
  }

}
