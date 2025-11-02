package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Rank;

public class OutputView {

    public void printIssuedLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }

    public void printResult(LottoResult lottoResult, int purchaseAmount) {
        System.out.println("\n당첨 통계");
        System.out.println("---");

        Map<Rank, Integer> statistics = lottoResult.getStatistics();

        System.out.printf("3개 일치 (%,d원) - %d개\n", Rank.FIFTH.getPrizeMoney(), statistics.get(Rank.FIFTH));
        System.out.printf("4개 일치 (%,d원) - %d개\n", Rank.FOURTH.getPrizeMoney(), statistics.get(Rank.FOURTH));
        System.out.printf("5개 일치 (%,d원) - %d개\n", Rank.THIRD.getPrizeMoney(), statistics.get(Rank.THIRD));
        System.out.printf("5개 일치, 보너스 볼 일치 (%,d원) - %d개\n", Rank.SECOND.getPrizeMoney(), statistics.get(Rank.SECOND));
        System.out.printf("6개 일치 (%,d원) - %d개\n", Rank.FIRST.getPrizeMoney(), statistics.get(Rank.FIRST));

        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
