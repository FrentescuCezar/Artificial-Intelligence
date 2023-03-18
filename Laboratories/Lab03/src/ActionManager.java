import java.util.Objects;

public class ActionManager {
    public static int[] pour(int[] currentState,int[] capacities, Main.ACTIONS into) {
        int[] state = currentState.clone();

        int fromJug = Objects.equals(into, Main.ACTIONS.FIRST) ? 1 : 0;
        int toJug = Objects.equals(into, Main.ACTIONS.FIRST) ? 0 : 1;

        if (!StateVerifier.canPour(state, capacities, fromJug, toJug)) {
            state[0] = -1;
            state[1] = -1;
            return state;
        }

        state[toJug] += state[fromJug];
        state[fromJug] = 0;
        int difference = state[toJug] - Main.capacities[toJug];
        if (difference > 0) {
            state[fromJug] = difference;
            state[toJug] = Main.capacities[toJug];
        }
        return state;
    }

    public static int[] fill(int[] currentState,int[] capacities, Main.ACTIONS jug) {
        int[] state = currentState.clone();

        int pos = Objects.equals(jug, Main.ACTIONS.FIRST) ? 0 : 1;
        if (!StateVerifier.canFill(state, capacities, pos)) {
            state[0] = -1;
            state[1] = -1;
            return state;
        }
        state[pos] = capacities[pos];
        return state;
    }

    public static int[] empty(int[] currentState, Main.ACTIONS jug) {
        int[] state = currentState.clone();
        int pos = Objects.equals(jug, Main.ACTIONS.FIRST) ? 0 : 1;
        if (!StateVerifier.canEmpty(state, pos)) {
            state[0] = -1;
            state[1] = -1;
            return state;
        }
        state[pos] = 0;
        return state;
    }

    static int[] doAction(int[] state,int[] capacities, Main.ACTIONS action) {

        switch (action) {
            case FILL_FIRST -> {
                return fill(state,capacities, Main.ACTIONS.FIRST);
            }
            case FILL_SECOND -> {
                return fill(state,capacities, Main.ACTIONS.SECOND);
            }
            case EMPTY_FIRST -> {
                return empty(state, Main.ACTIONS.FIRST);
            }
            case EMPTY_SECOND -> {
                return empty(state, Main.ACTIONS.SECOND);
            }
            case FIRST_TO_SECOND -> {
                return pour(state, capacities, Main.ACTIONS.SECOND);
            }
            case SECOND_TO_FIRST -> {
                return pour(state,capacities, Main.ACTIONS.FIRST);
            }
            default -> {
                return new int[2];
            }
        }
    }
}
