package com.dune.game.core.users_logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.BattleMap;
import com.dune.game.core.Building;
import com.dune.game.core.GameController;
import com.dune.game.core.Projectile;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.BattleTank;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.types.Owner;
import com.dune.game.core.units.types.UnitType;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;

public class AiLogic extends BaseLogic {
    private float timer;

    private List<BattleTank> tmpAiBattleTanks;
    private List<Harvester> tmpAiHarvesters;
    private List<Harvester> tmpPlayerHarvesters;
    private List<BattleTank> tmpPlayerBattleTanks;

    public AiLogic(GameController gc) {
        this.gc = gc;
        this.money = 1000;
        this.unitsCount = 10;
        this.unitsMaxCount = 100;
        this.ownerType = Owner.AI;
        this.tmpAiBattleTanks = new ArrayList<>();
        this.tmpAiHarvesters = new ArrayList<>();
        this.tmpPlayerHarvesters = new ArrayList<>();
        this.tmpPlayerBattleTanks = new ArrayList<>();
        this.timer = 10000.0f;
    }

    public void update(float dt) {
        timer += dt;
        if (timer > 2.0f) {
            timer = 0.0f;
            gc.getUnitsController().collectTanks(tmpAiBattleTanks, gc.getUnitsController().getAiUnits(), UnitType.BATTLE_TANK);
            gc.getUnitsController().collectTanks(tmpAiHarvesters, gc.getUnitsController().getAiUnits(), UnitType.HARVESTER);

            gc.getUnitsController().collectTanks(tmpPlayerHarvesters, gc.getUnitsController().getPlayerUnits(), UnitType.HARVESTER);
            gc.getUnitsController().collectTanks(tmpPlayerBattleTanks, gc.getUnitsController().getPlayerUnits(), UnitType.BATTLE_TANK);
            for (int i = 0; i < tmpAiBattleTanks.size(); i++) {
                BattleTank aiBattleTank = tmpAiBattleTanks.get(i);
                aiBattleTank.commandAttack(findNearestTarget(aiBattleTank, tmpPlayerBattleTanks));
            }

            for (int i = 0; i < tmpAiHarvesters.size(); i++) {
                Harvester aiHarvester = tmpAiHarvesters.get(i);

                if(aiHarvester.getContainer() == 0) {
                    goHarvest(aiHarvester);
                }
                if(aiHarvester.getContainer() >= 3){
                    goBase(aiHarvester);
                }
            }
        }
    }

    private void goBase(Harvester aiHarvester) {
        BattleMap map = gc.getMap();
        Vector2 base = new Vector2(14 * map.getCellSize(), 7 * map.getCellSize());
        aiHarvester.commandMoveTo(base, true);
    }

    private void goHarvest(Harvester aiHarvester){
        BattleMap map = gc.getMap();
        Vector2 dist = map.getClosestResourceCell(aiHarvester.getPosition());

        if(!anyHarvesterNearTarget(dist) ) {
           aiHarvester.commandMoveTo(dist, true);
        }

        if(anyHarvesterNearTarget(dist)) {
            goSomewhereRandom(aiHarvester);

        }
    }

    private void goSomewhereRandom(Harvester aiHarvester) {
        System.out.println( aiHarvester + " i need to go somewhere else");
        aiHarvester.commandMoveTo( new Vector2(MathUtils.random(10, 1270), MathUtils.random(10, 710)), true);
    }

    public <T extends AbstractUnit> T findNearestTarget(AbstractUnit currentTank, List<T> possibleTargetList) {
        T target = null;
        float minDist = 1000000.0f;
        for (int i = 0; i < possibleTargetList.size(); i++) {
            T possibleTarget = possibleTargetList.get(i);
            float currentDst = currentTank.getPosition().dst(possibleTarget.getPosition());
            if (currentDst < minDist) {
                target = possibleTarget;
                minDist = currentDst;
            }
        }
        return target;
    }

    private boolean anyHarvesterNearTarget(Vector2 trgt){
        for (int i = 0; i < tmpAiHarvesters.size(); i++) {
            Harvester aiHarvester = tmpAiHarvesters.get(i);

            if(Math.abs(trgt.dst(aiHarvester.getPosition()))< 40.0f)
                return true;
            }
        return false;
    }
}
