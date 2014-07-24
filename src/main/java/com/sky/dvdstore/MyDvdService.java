package com.sky.dvdstore;

/**
 * Created by Jiri on 24. 7. 2014.
 */
public class MyDvdService implements DvdService {

    DvdRepository repository;

    public MyDvdService(DvdRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dvd retrieveDvd(String dvdReference) throws DvdNotFoundException {
        return null;
    }

    @Override
    public String getDvdSummary(String dvdReference) throws DvdNotFoundException {
        return null;
    }
}
