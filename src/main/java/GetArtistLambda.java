import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.bson.Document;

import java.util.Map;

public class GetArtistLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final MongoConnection mongo;

    static {
        mongo = new MongoConnection();
    }

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);

        Map<String, String> map = event.getPathParameters();
        String artistId = map.get("artistId");
        if (artistId != null) {
            Document artistDoc = mongo.getArtistDocument(artistId);
            if (artistDoc != null) response.setBody(artistDoc.toJson());
        }
        return response;
    }
}
