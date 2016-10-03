package image;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class BlobImage {
	
	private BlobKey blobKey;
	private String servingUrl;
	private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
	public BlobImage(BlobKey blobKey) {
		this.blobKey = blobKey;
		servingUrl = null;
	}
	
	public String getUrl() {
		if (servingUrl != null) {
			return servingUrl;
		}
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
		options.imageSize(1600);
		servingUrl = imagesService.getServingUrl(options);
		return servingUrl;
	}
}
