package com.uitestcore.driverutils

import org.apache.commons.io.FileUtils
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


object ScreenshotUtils {
    private fun writeScreenshotToDir(file: String, dir: String, image: BufferedImage): String {
        val dest = System.getProperty("user.dir") + "/" + dir + "/" + file + ".png"
        val parentDir = File(dest).parentFile
        if(parentDir !=null && !parentDir.exists()){
            FileUtils.forceMkdir(parentDir)
        }
        ImageIO.write(image, "PNG", File(dest))
        return dest
    }

    fun takeAndSaveFullScreenshot(screenshotName: String, dir: String): String {
        val screenshot = AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(Driver.get())
        return writeScreenshotToDir(screenshotName, dir, screenshot.image)
    }

    fun takeFullScreenshot(): Screenshot? {
       return AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(Driver.get())
    }

    fun compareScreenshots(screenshotName: String): Boolean {
        val expectedImage = ImageIO.read(File(System.getProperty("user.dir") + "/ExpectedScreenshots/" + screenshotName + ".png"))
        val actualImage = takeFullScreenshot()!!.image

        val imgDiff = ImageDiffer()
        val diff = imgDiff.makeDiff(actualImage, expectedImage).withDiffSizeTrigger(1000)
        if (diff.hasDiff()) {
            println("Images are Not Same")
            takeAndSaveFullScreenshot(screenshotName, "ActualScreenshots")
            writeScreenshotToDir(screenshotName, "ResultScreenshots", diff.markedImage)
            return false
        } else {
            println("Images are Same")
        }
        return true
    }
}