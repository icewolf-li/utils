import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

@Slf4j
@Component
public class R2Utils {

    // 加载配置
    private static String accessKeyId = "<your-access-key-id>";
    private static String secretAccessKey = "<your-secret-access-key>";
    private static String endpoint = "https://<your-access-id>.r2.cloudflarestorage.com";
    private static String bucketName = "<your-bucket-name>";

    public static String uploadFile(MultipartFile file) throws IOException {
        // 创建S3客户端
        S3Client s3 = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)  // Cloudflare R2区域（选择合适的区域）
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();

        // 上传文件
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
                .build();

        PutObjectResponse response = s3.putObject(
                putObjectRequest,
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
        );
        log.info("File uploaded successfully: {}" , response.eTag());

        // 关闭S3客户端
        s3.close();
        // 拼接URL
        String url = "https://<your-domain>/"+file.getOriginalFilename();
        return url;
    }
}