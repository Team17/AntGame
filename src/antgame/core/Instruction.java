package antgame.core;

public enum Instruction {
	SENSE, MARK, UNMARK, PICKUP, DROP, TURN, MOVE, FLIP;

	public String toString() {
		switch (this) {
		case SENSE:
			return "Sense";
		case MARK:
			return "Mark";
		case UNMARK:
			return "Unmark";
		case PICKUP:
			return "PickUp";
		case DROP:
			return "Drop";
		case TURN:
			return "Turn";
		case MOVE:
			return "Move";
		case FLIP:
			return "Flip";
		default:
			// Unreachable block
			return "";
		}
	}
}