package pl.michaelslabikovsky.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LocationsDBModelTest {

    private static final String LOCATION_1 = "Lublin, PL";
    private static final String LOCATION_2 = "Praha, CZ";
    private static final List<String> exampleLocations = new ArrayList<>();

    @Mock
    LocationsDBModel dbMock;

    @BeforeAll
    static void setUp() {
        exampleLocations.add(LOCATION_1);
        exampleLocations.add(LOCATION_2);
    }

    @Test
    void selectAllFromDBShouldNotReturnNullIfQueryExecutionSucceeded() {
        //given
        given(dbMock.selectAllFromDB()).willReturn(exampleLocations);

        //when
        //then
        assertThat(dbMock.selectAllFromDB(), notNullValue());
    }

}