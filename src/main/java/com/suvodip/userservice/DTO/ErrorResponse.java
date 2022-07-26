package com.suvodip.userservice.DTO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class ErrorResponse {
	@NonNull String message;
}
