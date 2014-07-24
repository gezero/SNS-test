package com.sky.dvdstore;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jiri on 24. 7. 2014.
 */
public class MyDvdService implements DvdService {

    DvdRepository repository;
    private String DVD_PREFIX = "DVD-";
    private List charactersToRemove = Arrays.asList(' ',',');

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
        String review = returnFrist10Words(dvd.getReview());
        return String.format("[%s] %s - %s",dvd.getReference(), dvd.getTitle(), review);
    }

    private String returnFrist10Words(String string) {
        Pattern pattern = Pattern.compile("([\\S]+\\s*){1,10}");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()){
            throw new RuntimeException("Review does not contain any valid character...");
        }
        String shorterReview = matcher.group();
        if (shorterReview.equals(string)){
            return shorterReview;
        }
        return removeTrailingCharacters(shorterReview)+"...";
    }

    private String removeTrailingCharacters(String string) {
        for (int i = string.length()-1; i >0 ; --i) {
            if (! charactersToRemove.contains(string.charAt(i))){
                return string.substring(0,i+1);
            }
        }
        throw new RuntimeException("Review does not contain any valid character...");
    }
}
