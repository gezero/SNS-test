package com.sky.dvdstore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jiri on 24. 7. 2014.
 */
public class MyDvdService implements DvdService {

    DvdRepository repository;
    private String DVD_PREFIX = "DVD-";

    public MyDvdService(DvdRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dvd retrieveDvd(String dvdReference) throws DvdNotFoundException {
        checkNotNull(dvdReference);
        checkPrefix(dvdReference);
        Dvd dvd = repository.retrieveDvd(dvdReference);
        if (dvd == null){
            throw new DvdNotFoundException();
        }
        return dvd;
    }

    private void checkPrefix(String dvdReference) {
        if (dvdReference.startsWith(DVD_PREFIX)){
            return;
        }
        throw new MissingDVDPrefixException();
    }

    @Override
    public String getDvdSummary(String dvdReference) throws DvdNotFoundException {
        checkNotNull(dvdReference);
        checkPrefix(dvdReference);
        Dvd dvd = repository.retrieveDvd(dvdReference);
        if (dvd == null){
            throw new DvdNotFoundException();
        }
        return null;
    }
}
