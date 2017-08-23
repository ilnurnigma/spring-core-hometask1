package ua.epam.spring.hometask.utils;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class Every10thTicketDiscountStrategy implements DiscountStrategy{
    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        if (numberOfTickets % 10 == 0) {
            return 50;
        }

        return 0;
    }
}
