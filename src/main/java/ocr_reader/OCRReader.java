package ocr_reader;

public class OCRReader {
	public String imageUrl;
	
	public OCRReader(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public OCRResult read() {
		OCRReaderRequest request = new OCRReaderRequest(imageUrl);
		request.sendReadRequest();
		// TODO pass request results into ocr result
		OCRResult result = new OCRResult();
		result.make();
		return result;
	}
}
