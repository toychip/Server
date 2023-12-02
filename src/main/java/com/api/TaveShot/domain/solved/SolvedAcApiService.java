package com.api.TaveShot.domain.solved;

import static com.api.TaveShot.global.constant.SolvedAcConstant.SOLVED_REQUEST_USER_BIO_URI;

import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class SolvedAcApiService {

    public String getUserInfoFromSolvedAc(String handle) {
        HttpClient client = HttpClient.newHttpClient();
        String url = SOLVED_REQUEST_USER_BIO_URI + handle;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Accept", "application/json")
                    .GET() // GET 요청
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (URISyntaxException uriE) {
            uriE.printStackTrace();
            throw new ApiException(ErrorType._SOLVED_API_CONNECT_URI);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ApiException(ErrorType._SOLVED_API_CONNECT_HTTP);
        } catch (InterruptedException ie) {
            ie.printStackTrace();가
            throw new ApiException(ErrorType._SOLVED_API_CONNECT_INTERRUPT);
        }
    }
}
