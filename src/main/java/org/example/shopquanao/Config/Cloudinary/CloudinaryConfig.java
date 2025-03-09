package org.example.shopquanao.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary configKey(){
        Map<String,String> config=new HashMap<>();
        config.put("cloud_name", "dgkdd8x2a");
        config.put("api_key", "267632464771962");
        config.put("api_secret", "tbXSPQtkK5s-pUHNR5YIxgXV0Fw");
        return new Cloudinary(config);
    }
}
