package com.nexters.moyeomoyeo.common.util;


import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UuidGenerator {

	private static final Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

	public static String createUuid() {
		final UUID uuid = UUID.randomUUID();
		final byte[] bytes = uuidToBytes(uuid);
		return BASE64_URL_ENCODER.encodeToString(bytes);
	}

	private static byte[] uuidToBytes(UUID uuid) {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		byteBuffer.putLong(uuid.getMostSignificantBits());
		return byteBuffer.array();
	}
}
