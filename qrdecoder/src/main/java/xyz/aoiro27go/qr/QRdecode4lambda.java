package xyz.aoiro27go.qr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRdecode4lambda {

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
			throws IOException, NotFoundException, ChecksumException, FormatException, URISyntaxException {

		if (!event.getIsBase64Encoded()) {
			throw new UnsupportedOperationException("not base64 encoded");
		}

		// QRコードを読み取り
		byte[] decoded = Base64.getDecoder().decode(event.getBody());
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(decoded));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		// QRコードをデコード
		Reader reader = new MultiFormatReader();
		Result result = reader.decode(bitmap);

		// Header準備
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-type", "application/json");
		headerMap.put("Access-Control-Allow-Origin", "*");

		// 結果を返す
		APIGatewayProxyResponseEvent res = new APIGatewayProxyResponseEvent();
		res.setHeaders(headerMap);
		res.setStatusCode(200);
		res.setBody("{\"contents\":\"" + result.getText() + "\"}");

		return res;
	}
}
