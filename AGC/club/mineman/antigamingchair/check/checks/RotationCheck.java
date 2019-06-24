package club.mineman.antigamingchair.check.checks;

import club.mineman.antigamingchair.check.*;
import club.mineman.paper.event.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;

public abstract class RotationCheck extends AbstractCheck<PlayerUpdateRotationEvent>
{
    public RotationCheck(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, PlayerUpdateRotationEvent.class);
    }
}
