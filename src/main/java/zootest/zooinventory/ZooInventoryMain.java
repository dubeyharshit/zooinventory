package zootest.zooinventory;

import java.util.Scanner;

import zootest.zooinventory.Operations;

/*main class */

public class ZooInventoryMain {

	private static int zooId;
	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		boolean isExit = false;
		int option1, option2;
		System.out.print("Enter Zoo ID: ");
		zooId = in.nextInt(); // Get the zoo id from user to login to that particular zoo
		while (!isExit) {
			System.out.println("Menu\n1. Update Feed Invetory\n2. Fetch Stats\n3. Exit\n Enter your choice");
			option1 = in.nextInt();
			switch (option1) {
			case 1:
				System.out.println("Options\n1. Add shipments to new inventory\n2. Register feed times for each animal\n3. Replace running inventory with new inventory"
						+ "\nEnter your choice");
				option2 = in.nextInt();
				performInventoryUpdate(option2);
				break;
				
			case 2:
				System.out.println("Options\n1. Wastage per	zoo\n2. Species of animal at which zoos are being fed above/below "
						+ "average by species by given percentage\nEnter your choice");
				option2 = in.nextInt();
				getStats(option2);
				break;
			case 3:
				isExit = true;
				System.out.println("Thank You For Using Inventory System");
				break;
			default:
				System.out.println("Please Enter a number from 1,2 or 3");
				break;
}
		}
	}
	private static void performInventoryUpdate(int option) {
		switch (option) {
		case 1:
			System.out.print("Enter shipment quantity: ");
			double newQuantity = in.nextInt();
			Operations.addNewQuantity(zooId, newQuantity);
			break;
		case 2:
			Operations.recordFeedTime(zooId, in);
			break;
		case 3:
			Operations.replaceInventory(zooId);
			break;
		default:
			System.out.println("Wrong Option Please try Again");

		}
	}
	
	private static void getStats(int option) {
		switch (option) {
		case 1:
			Operations.getFoodWastagePerZoo();
			break;
		case 2:
			System.out.println("Enter percentage value: ");
			double percent = in.nextDouble();
			System.out.println("Animals above (1) or below (2) this percent?:");
			int isAboveOrBelow = in.nextInt();
			Operations.getAnimalFeedStat(zooId, percent, isAboveOrBelow);
			break;
		default:
			System.out.println("Wrong Option Please try Again");
		}
	}
}
