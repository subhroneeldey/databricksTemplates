// Databricks notebook source
import scala.util.control._

// COMMAND ----------

val configs = Map(
  "fs.azure.account.auth.type" -> "OAuth",
  "fs.azure.account.oauth.provider.type" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
  "fs.azure.account.oauth2.client.id" -> "ffe1dc29-d26c-441a-8aec-246ad0cfae93",
  "fs.azure.account.oauth2.client.secret" -> dbutils.secrets.get(scope = "keyvault-scope", key = "appsecret"),
  "fs.azure.account.oauth2.client.endpoint" -> "https://login.microsoftonline.com/67b2955f-1e72-4d33-8902-a48d63492efa/oauth2/token")


// COMMAND ----------


val mounts = dbutils.fs.mounts()
val mountPath = "/mnt/data"
var isExist: Boolean = false
val outer = new Breaks;

outer.breakable {
  for(mount <- mounts) {
    if(mount.mountPoint == mountPath) {
      isExist = true;
      outer.break;
    }
  }
}


// COMMAND ----------


if(isExist) {
  println("Volume Mounting for Case Study Data Already Exist!")
}
else {
  dbutils.fs.mount(
    source = "abfss://casestudydata@iomegadls.dfs.core.windows.net/",
    mountPoint = "/mnt/data",
    extraConfigs = configs)
}

// COMMAND ----------

