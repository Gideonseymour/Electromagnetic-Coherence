package resonantinduction.em.laser

import resonantinduction.em.{ElectromagneticCoherence, Vector3}
import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition

/**
 * @author Calclavia
 */
object LaserManager
{
  val maxDistance = 100

  def spawn(world: World, start: Vector3, direction: Vector3)
  {
    val startPos = start + direction + 0.5

    val maxPos = start + (direction * maxDistance) + 0.5
    val hit = world.rayTraceBlocks(startPos.toVec3, maxPos.toVec3)

    if (hit != null)
    {
      if (hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
      {
        val hitVec = new Vector3(hit.hitVec)
        val hitBlock = new Vector3(hit.blockX, hit.blockY, hit.blockZ)

        ElectromagneticCoherence.proxy.renderLaser(world, start + direction * 0.6 + 0.5, hitVec)
      }
    }
  }
}
