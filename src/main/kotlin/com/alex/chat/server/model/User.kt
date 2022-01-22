package com.alex.chat.server.model

import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class User(
    val name: String,
    val group: Group,
) {
    private val messageQueue: Queue<Message> = LinkedList()

    private val lock = ReentrantLock()

    private val lockCondition = lock.newCondition()

    fun sendMessage(message: Message) {
        lock.withLock {
            messageQueue.add(message)
            lockCondition.signalAll()
        }
    }

    fun pollMessage(): Message {
        lock.withLock {
            while (messageQueue.isEmpty()) {
                lockCondition.await()
            }

            return messageQueue.poll()
        }
    }
}