package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    public int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        String input = Console.readLine();

        validateNumberInput(input);

        return Integer.parseInt(input);
    }

    private void validateNumberInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }

    public List<Integer> readWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        String input = Console.readLine();

        validateWinningNumbersInput(input);

        List<Integer> winningNumbers = new ArrayList<Integer>();

        for (String numberStr : input.split(",")) {
            winningNumbers.add(Integer.parseInt(numberStr.trim()));
        }
        return winningNumbers;
    }

    private void validateWinningNumbersInput(String input) {
        String[] numbers = input.split(",");

        try {
            for (String numberStr : numbers) {
                Integer.parseInt(numberStr.trim());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자 형식이어야 합니다.");
        }
    }

    public int readBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        String input = Console.readLine();

        validateNumberInput(input);

        return Integer.parseInt(input);
    }

}
