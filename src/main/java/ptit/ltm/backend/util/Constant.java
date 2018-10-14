package ptit.ltm.backend.util;

public interface Constant {
	String ERROR = "1";
	String SUCCESS = "0";

	String ONLINE_STATUS = "0";
	String OFFLINE_STATUS = "1";
	String AVAILABLE_STATUS = "2";
	String BUSY_STATUS = "3";
	
    String SEND_ATTACK_MSG = " You want play with ";
    String ATTACK_REQUEST_MSG = " Play with you? ";
    String ACCEPT_MSG = " Accept this game ";
    
    int LOST = 0;
    int DRAW = 1;
    int WIN = 2;
}
