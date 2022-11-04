package com.micro
package persistence.example

import java.time.Instant
import java.util.UUID

case class ExampleEntity(id: UUID, name: String, dateTime: Instant, note: Option[String])
