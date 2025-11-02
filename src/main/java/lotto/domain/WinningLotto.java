package lotto.domain;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validate(Lotto winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public Rank determineRank(Lotto userLotto) {
        int matchCount = userLotto.countMatchingNumbers(winningNumbers);
        boolean matchBonus = userLotto.contains(bonusNumber);

        return Rank.valueOf(matchCount, matchBonus);
    }
}
