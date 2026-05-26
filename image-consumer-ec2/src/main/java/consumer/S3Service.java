package consumer;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3Service implements AutoCloseable {
    private final S3Client client;
    private final String defaultBucket;

    public S3Service(Region region, String defaultBucket) {
        this.client = S3Client.builder().region(region).build();
        this.defaultBucket = defaultBucket;
    }

    public ResponseBytes<GetObjectResponse> downloadImage(String bucket, String key) {
        return client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }

    public void uploadImage(String bucketName, String fileName, byte[] data) {
        client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key("processed/" + fileName)
                        .contentType("image/jpeg")
                        .build(),
                RequestBody.fromBytes(data));
    }

    @Override
    public void close() {
        client.close();
    }
}
