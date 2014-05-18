package resonantinduction.em.laser.emitter

import resonantinduction.em.{ElectromagneticCoherence, Vector3}
import resonantinduction.em.laser.{Laser, TileBase, ILaserHandler}
import net.minecraft.util.MovingObjectPosition

/**
 * @author Calclavia
 */
class TileLaserReceiver extends TileBase with ILaserHandler
{
  private var energy = 0D

  var redstoneValue = 0
  private var prevRedstoneValue = 0;

  override def updateEntity()
  {
    if (energy > 0)
    {
      redstoneValue = Math.min(Math.ceil(energy / (Laser.maxEnergy / 15)), 15).toInt

      if (redstoneValue != prevRedstoneValue)
      {
        world.notifyBlocksOfNeighborChange(x, y, z, getBlockType)
        prevRedstoneValue = redstoneValue
      }

      energy = 0
    }
    else
    {
      redstoneValue = 0

      if (redstoneValue != prevRedstoneValue)
      {
        world.notifyBlocksOfNeighborChange(x, y, z, getBlockType)
        prevRedstoneValue = redstoneValue
      }
    }
  }

  override def onLaserHit(renderStart: Vector3, incident: Vector3, hit: MovingObjectPosition, color: Vector3, energy: Double): Boolean =
  {
    if (hit.sideHit == direction.ordinal)
    {
      ElectromagneticCoherence.proxy.renderLaser(world, renderStart, position + 0.5 + new Vector3(direction) * 0.3, color, energy)
      //TODO: Change this
      this.energy += energy
      return true
    }

    return false
  }
}
