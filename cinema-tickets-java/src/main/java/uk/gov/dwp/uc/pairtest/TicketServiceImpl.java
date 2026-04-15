package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;


public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
   private final TicketPaymentServiceImpl paymentService;
   private final SeatReservationServiceImpl seatReservationService;

    public TicketServiceImpl() {
        this.paymentService = new TicketPaymentServiceImpl();
        this.seatReservationService = new SeatReservationServiceImpl();
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        validateAccount(accountId);

        int adultCount = 0;
        int childCount = 0;
        int infantCount = 0;

        for (TicketTypeRequest request : ticketTypeRequests) {
            switch (request.getTicketType()) {
                case ADULT:
                    adultCount += request.getNoOfTickets();
                    break;
                case CHILD:
                    childCount += request.getNoOfTickets();
                    break;
                case INFANT:
                    infantCount += request.getNoOfTickets();
                    break;
            }
        }
        int totalTickets = adultCount + childCount + infantCount;

        validateBusinessRules(adultCount, childCount, infantCount, totalTickets);

        int totalAmount = (adultCount * 25) + (childCount * 15);
        int totalSeats = adultCount + childCount;

        paymentService.makePayment(accountId, totalAmount);
        seatReservationService.reserveSeat(accountId, totalSeats);
    }

    private void validateAccount(long accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
    }

    private void validateBusinessRules(int adult, int child, int infant, int total) {

        if (total == 0) {
            throw new IllegalArgumentException("No tickets requested");
        }
        if (total > 25) {
            throw new IllegalArgumentException("Max 25 tickets allowed");
        }
        if (adult == 0 && (child > 0 || infant > 0)) {
            throw new IllegalArgumentException("Child or Infant cannot be purchased without Adult");
        }
        if (infant > adult) {
            throw new IllegalArgumentException("Infants must sit on an adult lap");
        }
    }
}
