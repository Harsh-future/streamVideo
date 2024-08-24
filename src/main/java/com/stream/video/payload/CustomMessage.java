package com.stream.video.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomMessage {

    private String message;

    private boolean success=false;
}
