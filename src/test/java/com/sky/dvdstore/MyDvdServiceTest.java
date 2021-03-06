package com.sky.dvdstore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

public class MyDvdServiceTest {

    @InjectMocks
    MyDvdService myDvdService;

    @Mock
    DvdRepository dvdRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected=MissingDVDPrefixException.class)
    public void testRetrieveDvdTextMustContainDVDPrefix() throws Exception {
        myDvdService.retrieveDvd("NotHavingDVD-Prefix");
    }
    @Test(expected=NullPointerException.class)
    public void testRetrieveDvdNull() throws Exception {
        myDvdService.retrieveDvd(null);
    }

    @Test(expected=DvdNotFoundException.class)
    public void testRetrieveDvdNotFound() throws Exception {
        myDvdService.retrieveDvd("DVD-999");
    }

    @Test
    public void testRetrieveDvdFound() throws Exception {
        Dvd dvd = new Dvd("DVD-TG423","Topgun", "All action film");
        when(dvdRepository.retrieveDvd("DVD-999")).thenReturn(dvd);
        Dvd retrievedDvd = myDvdService.retrieveDvd("DVD-999");
        assertThat(retrievedDvd,is(dvd));
    }

    @Test(expected=MissingDVDPrefixException.class)
    public void testGetDvdSummaryDvdTextMustContainDVDPrefix() throws Exception {
        myDvdService.getDvdSummary("NotHavingDVD-Prefix");
    }

    @Test(expected=DvdNotFoundException.class)
    public void testGetDvdSummaryDvdNotFound() throws Exception {
        myDvdService.getDvdSummary("DVD-999");
    }
    @Test(expected=NullPointerException.class)
    public void testGetDvdSummaryDvdNull() throws Exception {
        myDvdService.getDvdSummary(null);
    }

    @Test
    public void testGetDvdSummaryShortSumary() throws DvdNotFoundException {
        Dvd dvd = new Dvd("DVD-TG423","Topgun", "All action film");
        when(dvdRepository.retrieveDvd("DVD-999")).thenReturn(dvd);
        String retrievedDvd = myDvdService.getDvdSummary("DVD-999");
        assertThat(retrievedDvd, is(equalTo("[DVD-TG423] Topgun - All action film")));
    }

    @Test
    public void testGetDvdSummaryLongSumary() throws DvdNotFoundException {
        Dvd dvd = new Dvd("DVD-S765","Shrek", "Big green monsters, they're just all " +
                "the rage these days, what with Hulk, Yoda, and that big ugly troll " +
                "thingy out of the first Harry Potter movie. But Shrek, the flatulent " +
                "swamp-dwelling ogre with a heart of gold (well, silver at least), " +
                "is more than capable of rivalling any of them.");
        when(dvdRepository.retrieveDvd("DVD-999")).thenReturn(dvd);
        String retrievedDvd = myDvdService.getDvdSummary("DVD-999");
        assertThat(retrievedDvd, is("[DVD-S765] Shrek - Big green monsters, they're just all the rage these days..."));
    }

}