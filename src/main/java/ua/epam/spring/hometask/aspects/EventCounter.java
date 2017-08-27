package ua.epam.spring.hometask.aspects;

public class EventCounter {
    private long nameAccessCounter;
    private long priceAccesCounter;
    private long bookTicketCounter;

    public void addNameAccessCounter() {
        ++nameAccessCounter;
    }

    public long getPriceAccesCounter() {
        return priceAccesCounter;
    }

    public long getNameAccessCounter() {
        return nameAccessCounter;
    }

    public void addPriceAccessCounter() {
        ++priceAccesCounter;
    }

    public long getBookTicketCounter() {
        return bookTicketCounter;
    }

    public void addBookTicketCounter() {
        ++bookTicketCounter;
    }
}
