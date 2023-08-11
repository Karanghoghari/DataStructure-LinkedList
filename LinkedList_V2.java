
import java.util.*;

class Supply implements Comparable<Supply> {
    int retailer;
    Date date;
    int supplier;
    int requirement;

    public Supply(int retailer, Date date, int supplier, int requirement) {
        this.retailer = retailer;
        this.date = date;
        this.supplier = supplier;
        this.requirement = requirement;
    }

    public int compareTo(Supply other) {
        return this.date.compareTo(other.date);
    }

    public String toString() {
        return "Retailer: " + (retailer < 10 ? ("0" + retailer) : retailer) + " | Date: " + date + " | Requirement: "
                + (requirement < 10 ? ("0" + requirement) : requirement);
    }
}

public class Linked_List02 {
    public static void main(String[] args) {

        // Create a Linked List
        LinkedList<Supply> supplyList = new LinkedList<>();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of Retailer: ");
        int numRetailer = sc.nextInt();
        System.out.print("Enter the number of Supplies: ");
        int totalSupplies = sc.nextInt();

        Random random = new Random();

        for (int retailer = 1; retailer <= numRetailer; retailer++) {

            // Random date between 1 to 30
            int day = random.nextInt(30) + 1;

            // To get Date
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2023);
            calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date date = calendar.getTime();

            // Random supplier between 1 and 100
            int supplier = random.nextInt(totalSupplies) + 1;

            // Random requirement between 1 and 100
            int requirement = random.nextInt(totalSupplies) + 1;

            Supply supply = new Supply(retailer, date, supplier, requirement);

            // Insert supply into the linked list while maintaining sorted order
            ListIterator<Supply> iterator = supplyList.listIterator(); // Retrieve elements one by one
            boolean inserted = false;

            while (iterator.hasNext()) {
                if (supply.compareTo(iterator.next()) < 0) {
                    iterator.previous(); // NewSupply < NextSupply -> the iterator is moved back to the previous position.
                    iterator.add(supply);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                iterator.add(supply);
            }
        }

        // Print the sorted supply list
        System.out.println("\n-------------------------SORTED SUPPLY LIST-------------------------\n");

        for (Supply supply : supplyList) {
            System.out.println(supply);
        }
        // int remainingSupplies = 100;

        // Distribute supplies and calculate remaining requirements
        for (Supply supply : supplyList) {
            if (totalSupplies >= supply.requirement) {
                totalSupplies -= supply.requirement;
                supply.supplier = supply.requirement;
            } else {
                supply.supplier = totalSupplies;
                totalSupplies = 0;
            }
        }

        // Calculate and print unsatisfied requirements for each retailer
        System.out.println("\n---------------Unsatisfied Requirements per Retailer---------------\n");
        for (Supply supply : supplyList) {
            int unsatisfied = Math.max(supply.requirement - supply.supplier, 0);

            if (unsatisfied == 0) {
                System.out.println("Retailer " + (supply.retailer < 10 ? ("0" + supply.retailer) : supply.retailer)
                        + ": Satisfied");
            } else {
                System.out
                        .println("Retailer " + (supply.retailer < 10 ? ("0" + supply.retailer) : supply.retailer) + ": "
                                + unsatisfied + " unsatisfied");
            }
        }

        // Calculate total unsatisfied requirements
        int totalUnsatisfied = supplyList.stream().mapToInt(supply -> Math.max(supply.requirement - supply.supplier, 0))
                .sum();

        System.out.println("\nTotal Unsatisfied Requirements: " + totalUnsatisfied + "\n");
    }
}

// stream(): This method is called on the supplyList, creating a stream of
// elements from the list. A stream is a sequence of elements that you can
// process in various ways using functional-style operations.
