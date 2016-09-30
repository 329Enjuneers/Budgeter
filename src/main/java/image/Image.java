package image;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class Image {
	
	private BlobKey blobKey;
	private String servingUrl;
	private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
	public Image(BlobKey blobKey) {
		this.blobKey = blobKey;
		servingUrl = null;
	}
	
	public String getUrl() {
		if (servingUrl != null) {
			return servingUrl;
		}
		servingUrl = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey));
		return servingUrl;
	}
}
