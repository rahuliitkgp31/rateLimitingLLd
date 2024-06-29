package org.example.Controller;

import org.example.Request;
import org.example.rateLimiting.RateLimitingChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class controller {

    @Autowired
    RateLimitingChain rateLimitingChain;

    @GetMapping("/v1/{userId}/{deviceId}")
    public ResponseEntity<Boolean> isRequestValid(@PathVariable String userId,
                                                 @PathVariable String deviceId){
        Request request = new Request(userId, deviceId, null);
        boolean result = rateLimitingChain.isRequestAllowed(request);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
