package dk.obhnothing.persistence.dao;

import java.util.Set;

import dk.obhnothing.persistence.dto.TripDTO;

public interface ITripGuideDAO
{

    void addGuideToTrip(int tripId, int guideId);
    Set<TripDTO> getTripsByGuide(int guideId);

}


