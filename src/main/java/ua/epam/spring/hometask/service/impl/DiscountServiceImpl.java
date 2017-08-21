package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {
    private List<Every10thTicketDiscountStrategy> discountStrategies = new ArrayList<>();

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        byte maxDiscount = 0;

        for (Every10thTicketDiscountStrategy strategy : discountStrategies) {
            byte discount = strategy.getDiscount(user, event, airDateTime, numberOfTickets);
            if (maxDiscount > discount) {
                maxDiscount = discount;
            }
        }

        return maxDiscount;
    }
}
