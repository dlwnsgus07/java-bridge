package bridge;

import bridge.command.MoveCommand;
import bridge.command.RestartCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

    private final Bridge bridge;
    private List<String> movingPositions;
    private List<String> result;
    private int count;
    private boolean clear;
    private boolean failure;

    public BridgeGame(Bridge bridge) {
        this.bridge = bridge;
        this.movingPositions = new ArrayList<>();
        this.result = new ArrayList<>();
        this.clear = false;
        this.failure = false;
        this.count = 1;
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void move(String movePosition) {
        positionValidation(movePosition);
        movingPositions.add(movePosition);
    }

    public void positionValidation(String position) {
        if (!Objects.equals(position, MoveCommand.UP.getCommand())
                && !Objects.equals(position, MoveCommand.DOWN.getCommand())) {
            throw new IllegalArgumentException("이동하는 칸은 \"" + MoveCommand.UP.getCommand()
                    + "\"(위칸), \"" + MoveCommand.DOWN.getCommand() + "\"(아래칸) 만 가능합니다.");
        }
    }

    public void comparedBridge() {
        String bridgeValue = bridge.getBridge().get(movingPositions.size() - 1);
        String movingPosition = movingPositions.get(movingPositions.size() - 1);
        addResult(bridgeValue, movingPosition);
    }

    private void addResult(String bridgeValue, String movingPosition) {
        if (Objects.equals(bridgeValue, movingPosition)) {
            result.add("O");
        }
        if (!Objects.equals(bridgeValue, movingPosition)) {
            result.add("X");
            setFailure(true);
        }
        if (!failure && bridge.getBridge().size() == movingPositions.size()) {
            setClear(true);
        }
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry(String command) {
        commandValidation(command);
        if (Objects.equals(command, RestartCommand.RESTART.getCommand())) {
            init();
            count++;
        }
        if (Objects.equals(command, RestartCommand.QUIT.getCommand())) {
            setClear(true);
        }
    }

    private void init() {
        setMovingPositions(new ArrayList<>());
        setResult(new ArrayList<>());
        setFailure(false);
        setClear(false);
    }

    private void commandValidation(String command) {
        if (!Objects.equals(command, RestartCommand.RESTART.getCommand())
                && (!Objects.equals(command, RestartCommand.QUIT.getCommand()))) {
            throw new IllegalArgumentException("재시도는 \""
                    + RestartCommand.RESTART.getCommand()
                    + "\" , 종료는 \""
                    + RestartCommand.QUIT.getCommand() + "\"를 누르셔야 합니다.");
        }
    }

    public List<String> getMovingPositions() {
        return movingPositions;
    }

    public boolean isClear() {
        return clear;
    }

    public boolean isFailure() {
        return failure;
    }

    public List<String> getResult() {
        return result;
    }

    private void setMovingPositions(List<String> movingPositions) {
        this.movingPositions = movingPositions;
    }

    private void setResult(List<String> result) {
        this.result = result;
    }

    private void setClear(boolean clear) {
        this.clear = clear;
    }

    private void setFailure(boolean failure) {
        this.failure = failure;
    }

    public int getCount() {
        return count;
    }
}
