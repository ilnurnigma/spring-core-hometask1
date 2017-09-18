package ua.epam.spring.hometask.service.strategies;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class Every10thTicketDiscountStrategy implements DiscountStrategy{
    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        if (numberOfTickets<10) {
            return 0;
        }

        long numberOfDiscountTickets = numberOfTickets / 10;
        long numberOfTicketsWithoutDiscount = numberOfTickets - numberOfDiscountTickets;

        double valueOfOneTicket = 100.0 / numberOfTickets;
        double valueOfTicketsWithoutDiscount = numberOfTicketsWithoutDiscount * valueOfOneTicket;
        double valueOfDiscountedTickets = valueOfOneTicket * numberOfDiscountTickets/2;

        double totalDiscount = 100 - (valueOfTicketsWithoutDiscount + valueOfDiscountedTickets);

        return (byte) Math.round(totalDiscount);
    }
}
