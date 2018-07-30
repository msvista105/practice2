package com.example.sxm.handler;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    private static final String TAG = MessageQueue.class.getSimpleName();
    private static final int MESSAGE_SIZE = 50;
    private Message[] mMessages = new Message[MESSAGE_SIZE];
    //message 数量
    private int mCount = 0;

    private int mTakeIndex;
    private int mPutIndex;

    private Lock mLock;
    private Condition mNotFull;
    private Condition mNotEmpty;

    public MessageQueue() {
        mLock = new ReentrantLock();
        mNotEmpty = mLock.newCondition();
        mNotFull = mLock.newCondition();
    }

    public void enqueueMessage(Message msg) {
        try {
            mLock.lock();
            while (mCount >= MESSAGE_SIZE) {
                //阻塞
                mNotFull.await();
            }
            mMessages[mPutIndex] = msg;
            mCount++;
            mPutIndex = (++mPutIndex >= MESSAGE_SIZE) ? 0 : mPutIndex;
            mNotEmpty.signalAll();
        } catch (Exception e) {
            //
        } finally {
            mLock.unlock();
        }
    }

    public Message next() {
        Message msg = null;
        try {
            mLock.lock();
            if (mCount <= 0) {
                mNotEmpty.wait();
            }
            msg = mMessages[mTakeIndex];
            mMessages[mTakeIndex] = null;
            mCount--;
            mTakeIndex = (++mTakeIndex >= MESSAGE_SIZE) ? 0 : mTakeIndex;
            mNotFull.signalAll();
        } catch (Exception e) {
            //
        } finally {
            mLock.unlock();
        }
        return msg;
    }
}
