import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 * 
	 * Create a zero, then create a nested for loop to compare
	 */

	private static TNode makeNodes(TNode first, int location){
		TNode temp= new TNode();
		temp.location = location;
		temp.next = null;
		temp.down = null;

		TNode ptr = first;
		while (ptr.next != null){
			ptr = ptr.next;
		}
		ptr.next= temp;
		return first;

		
	}
	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations){

		TNode head= new TNode();
		TNode head1= new TNode();
		TNode head2= new TNode();


		for (int a = 0; a < trainStations.length; a++){
			head= makeNodes(head, trainStations[a]);
		}
		
		for (int b = 0; b < busStops.length; b++){
			head1= makeNodes(head1, busStops[b]);
		}

		for (int c = 0; c < locations.length; c++){
			head2= makeNodes(head2, locations[c]);
		}

		for(TNode ptr = head; ptr != null; ptr= ptr.next){
			for(TNode ptr2 = head1; ptr2 != null; ptr2= ptr2.next){
				if (ptr.location == ptr2.location){
					ptr.down= ptr2;
				}
			}
		}
		for(TNode ptr2= head1; ptr2 != null; ptr2= ptr2.next){
			for(TNode ptr3= head2; ptr3 != null; ptr3= ptr3.next){
				if(ptr2.location== ptr3.location){
					ptr2.down=ptr3;
				}
			}
		}
		 return head;
	}
	
	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station){
		for(TNode ptr= trainZero; ptr != null; ptr= ptr.next){
			if(ptr != null && ptr.location == station){
				return;
			} else{
				if(ptr.next != null && ptr.next.location == station){
					ptr.next= ptr.next.next;
					break;
				}
			}
		}
	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop){

		TNode holdVal= new TNode();
		TNode holdWalk= new TNode();
		TNode ptr2= trainZero.down;
		TNode ptr= trainZero.down.down;

		for (ptr = trainZero.down.down; ptr != null; ptr= ptr.next){
			if(ptr.location == busStop){
				holdWalk=ptr;
				while(ptr2 != null && ptr2.location <= busStop){
					holdVal= ptr2;
					ptr2= ptr2.next;
					if(ptr2 != null && ptr2.location == busStop){
						return;
				}	
			}
		}
		}
			TNode newStop= new TNode();
			newStop.location= busStop;
			holdVal.next= newStop;
			newStop.next=ptr2;
			newStop.down=holdWalk;
	}	
	
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {

		ArrayList<TNode> goodPath= new ArrayList<TNode>();
		TNode finder= trainZero;
		while(finder.next != null && finder.next.location <= destination){
			goodPath.add(finder);
			if(finder.location == destination){
				return goodPath;
			}
			finder= finder.next;
		}
		goodPath.add(finder);
		TNode finder2= finder.down;
		while(finder2.next != null && finder2.next.location <= destination){
			StdOut.println(finder2.location);
			goodPath.add(finder2);
			if(finder.location == destination){
				return goodPath;
			}
			finder2=finder2.next;
		}
		goodPath.add(finder2);
		TNode finder3= finder2.down;
		while(finder3.location <= destination){
			goodPath.add(finder3);
			if(finder3.location == destination){
				return goodPath;
			}
			finder3=finder3.next;
		}
		
		return goodPath;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {

		TNode oldTrain= trainZero;
		TNode oldBus= trainZero.down;
		TNode oldWalk= trainZero.down.down;

		TNode newTrain= new TNode(oldTrain.location);
		TNode newBus= new TNode(oldBus.location);
		TNode newWalk= new TNode(oldWalk.location);

		TNode prev= newTrain;
		TNode prev2= newBus;
		TNode prev3=newWalk;

		for(TNode ptr= oldTrain.next; ptr != null; ptr= ptr.next){
			TNode val= new TNode(ptr.location);
			prev.next= val;
			prev= val;
			System.out.println("Node: " + val.location + " " + val);
		}

		for(TNode ptr= oldBus; ptr != null; ptr= ptr.next){
			TNode val= new TNode(ptr.location);
			prev2.next=val;
			prev2=val;
			System.out.println("Node: " + val.location + " " + val);
		}

		for(TNode ptr= oldWalk; ptr != null; ptr= ptr.next){
			TNode val= new TNode(ptr.location);
			prev3.next=val;
			prev3=val;
			System.out.println("Node: " + val.location + " " + val);
		}

		for(TNode ptr= newTrain; ptr != null; ptr= ptr.next){
			for(TNode ptr2= newBus; ptr2 != null; ptr2=ptr2.next){
				if(ptr.location == ptr2.location){
					ptr.down=ptr2;
				}
			}
		}

		for(TNode ptr2= newBus; ptr2 != null; ptr2=ptr2.next){
			for(TNode ptr3= newWalk; ptr3 != null; ptr3=ptr3.next){
				if(ptr2.location == ptr3.location){
					ptr2.down=ptr3;
				}
			}
		}

		return newTrain;

	}


	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {

		TNode head= new TNode();
		TNode walkZero= trainZero.down.down;

		for (int a = 0; a < scooterStops.length; a++){
			head= makeNodes(head, scooterStops[a]);
		}

		for(TNode ptr= trainZero.down; ptr != null; ptr = ptr.next){
			for(TNode ptr2= head; ptr2!= null; ptr2=ptr2.next){
				if(ptr.location == ptr2.location){
					ptr.down=ptr2;
				}
			}
		}
		// if you put trainZero.down.down instead of walkZero then 
		//it goes to the scooter layer since we initialized the scooter
		//below the bus creating an infinite loop
		for(TNode ptr2=head; ptr2!= null; ptr2= ptr2.next){
			for(TNode ptr3= walkZero; ptr3 != null; ptr3= ptr3.next){ 
				if(ptr2.location == ptr3.location){
					ptr2.down=ptr3;
				}
			}
		}
	}
}