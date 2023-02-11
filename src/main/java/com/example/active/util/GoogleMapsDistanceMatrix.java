package com.example.active.util;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import java.util.Calendar;

public class GoogleMapsDistanceMatrix {
    private static final String API_KEY = "YOUR_GOOGLE_MAPS_API_KEY";
    private static final GeoApiContext context = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
            .apiKey(API_KEY)
            .build();

    public DistanceMatrix estimateRouteTime( DirectionsApi.RouteRestriction routeRestriction, LatLng departure, LatLng... arrivals) {
        try {
            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
            req.departureTime(Calendar.getInstance().toInstant());
            if (routeRestriction == null) {
                routeRestriction = DirectionsApi.RouteRestriction.TOLLS;
            }
            DistanceMatrix trix = req.origins(departure)
                    .destinations(arrivals)
                    .mode(TravelMode.DRIVING)
                    .avoid(routeRestriction)
                    .await();
            return trix;
        } catch (ApiException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}