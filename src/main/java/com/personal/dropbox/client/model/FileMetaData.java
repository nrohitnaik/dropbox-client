package com.personal.dropbox.client.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileMetaData {

  private String name;
  private long size;
  private String id;

  @SerializedName(".tag")
  private String tag;

  @SerializedName("path_lower")
  private String path;

  @SerializedName("server_modified")
  private LocalDateTime serverModifiedDate;
}
