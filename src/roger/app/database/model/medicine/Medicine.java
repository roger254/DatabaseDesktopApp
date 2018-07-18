package roger.app.database.model.medicine;

import java.time.LocalDate;

public class Medicine
{
  private static int nextId = 1; //keep note of how many medicine items are available
  private String medicineName;
  private double price;
  private int quantity;
  private LocalDate entryDate; // date of entry
  private int orderId;

  public Medicine(String medicineName, double price, int quantity, int year, int month, int date)
  {
    this.medicineName = medicineName;
    this.price = price;
    this.quantity = quantity;
    this.entryDate = LocalDate.of(year, month, date);
    this.orderId = nextId;
    nextId++;
  }

  public String getName()
  {
    return medicineName;
  }

  public double getPrice()
  {
    return price;
  }

  public double getQuantity()
  {
    return quantity;
  }

  public LocalDate getEntryDate()
  {
    return entryDate;
  }

  public int getOrderId()
  {
    return orderId;
  }

}
