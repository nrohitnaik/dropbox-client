package com.personal.dropbox.client;

import com.personal.dropbox.client.console.DropboxConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DropboxClientApplication implements ApplicationRunner {

  @Autowired
  private DropboxConsole dropboxConsole;

  public static void main(String[] args) {
    SpringApplication.run(DropboxClientApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    dropboxConsole.init(args.getSourceArgs());
  }
}
