package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import static org.junit.jupiter.api.Assertions.*;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;

class TicketServiceImplTest {

    private final TicketServiceImpl service = new TicketServiceImpl();

    // ---------------- VALID CASE ----------------
    @Test
    void shouldAllowValidAdultChildPurchase() {
        assertDoesNotThrow(() ->
                service.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
                )
        );
    }

    // ---------------- INVALID SCENARIOS ----------------
    @Test
    void shouldRejectInvalidAccountId() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(
                        0L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );
    }

    @Test
    void shouldRejectChildTicketWithoutAdult() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
                )
        );
    }

    @Test
    void shouldRejectInfantTicketWithoutAdult() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )
        );
    }

    @Test
    void shouldRejectMoreThan25TotalTickets() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26)
                )
        );
    }

    @Test
    void shouldRejectMoreInfantsThanAdults() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
                )
        );
    }

    @Test
    void shouldRejectEmptyTicketRequest() {
        assertThrows(IllegalArgumentException.class, () ->
                service.purchaseTickets(1L)
        );
    }
}
