package sensorserver.dataProviders.vendors;

import com.google.inject.Inject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import sensorserver.exceptions.MacAddressException;
import sensorserver.exceptions.SensorException;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Mac2VendorProvider implements IVendorsProvider {
    private String _apiUrl;

    @Inject
    public Mac2VendorProvider(@Named("vendorsApiUrl") String apiUrl) {
        _apiUrl = apiUrl;
    }

    @Override
    public Future<String> getVendorByMac(String mac) {
        CompletableFuture<String> promise = new CompletableFuture<>();

        Unirest.get(_apiUrl + "/mac/{macAddr}")
                .routeParam("macAddr", mac)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        JSONObject responseBody = httpResponse.getBody().getObject();

                        if (responseBody.getBoolean("success")) {
                            JSONObject payload = responseBody.getJSONArray("payload").getJSONObject(0);
                            String vendor = payload.getString("vendor");
                            promise.complete(vendor);
                        } else {
                            String errorMsg = responseBody.getString("message");
                            MacAddressException exception = new MacAddressException(mac, errorMsg);
                            promise.completeExceptionally(exception);
                        }
                    }

                    @Override
                    public void failed(UnirestException e) {
                        promise.completeExceptionally(new SensorException(e.getMessage(), e));
                    }

                    @Override
                    public void cancelled() {

                    }
                });


        return promise;
    }
}
