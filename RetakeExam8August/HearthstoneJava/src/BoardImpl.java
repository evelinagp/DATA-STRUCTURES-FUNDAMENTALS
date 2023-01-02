import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardImpl implements Board {

    Map<String, Card> deckOfCards;

    public BoardImpl() {
        this.deckOfCards = new HashMap<>();
    }

    @Override
    public void draw(Card card) {
        if (contains(card.getName())) {
            throw new IllegalArgumentException();
        }
        this.deckOfCards.put(card.getName(), card);
    }

    @Override
    public Boolean contains(String name) {
        if (!this.deckOfCards.containsKey(name)) {
            return false;
            /* throw new IllegalArgumentException();*/
        }
        return true;
    }

    @Override
    public int count() {
        return this.deckOfCards.size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        boolean canPlay = true;
        Card attackerCard = this.deckOfCards.get(attackerCardName);
        Card attackedCard = this.deckOfCards.get(attackedCardName);
        if (attackerCard == null || attackedCard == null) {
            throw new IllegalArgumentException();
        }
        if (attackerCard.getLevel() != attackedCard.getLevel()) {
            throw new IllegalArgumentException();
        }
        if (attackerCard.getHealth() <= 0 || attackedCard.getHealth() <= 0) {
            return;
        }

        int attackedCardDamage = attackedCard.getDamage();
        int attackedCardHealth = attackedCard.getHealth();
        int attackerCardScore = attackerCard.getScore();
        attackedCardDamage -= attackerCard.getDamage();
        attackedCardHealth -= attackerCard.getDamage();
        if (attackedCardHealth < 0) {
            canPlay = false;
            attackerCardScore += attackedCard.getLevel();
            return;
        }

    }

    @Override
    public void remove(String name) {
        if (!contains(name)) {
            throw new IllegalArgumentException();
        }
        this.deckOfCards.remove(name);
    }


    @Override
    public void removeDeath() {
        this.deckOfCards.values().removeIf(card ->
                card.getHealth() <= 0);
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        return this.deckOfCards.values().stream().filter(card ->
                        card.getScore() >= start && card.getScore() <= end)
                .sorted((c1, c2) -> Integer.compare(c2.getLevel(), c1.getLevel())).collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        return this.deckOfCards.values().stream().sorted((c1, c2) -> {
                    int result = c1.getName().compareTo(c2.getName());
                    if (result == 0) {
                        result = Integer.compare(c1.getLevel(), c2.getLevel());
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }
      /*  for (Card card : deckOfCards.values()) {
            for (int i = 0; i < card.getName().length() / 2; i++) {

            }*/

    /*     .sorted((c1, c2) -> Integer.compare(c2.getScore(), c1.getScore())).collect(Collectors.toList());*/


    @Override
    public Iterable<Card> searchByLevel(int level) {
        List<Card> collect = this.deckOfCards.values().stream().filter(card ->
                        card.getLevel() == level)
                .sorted((c1, c2) -> Integer.compare(c2.getScore(), c1.getScore())).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void heal(int health) {
        List<Card> smallest = this.deckOfCards.values().stream()
                .sorted(Comparator.comparingInt(Card::getHealth)).collect(Collectors.toList());
        if (!smallest.isEmpty()) {
            int i = smallest.get(0).getHealth() + health;
        }
    }

}
