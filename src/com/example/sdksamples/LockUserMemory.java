package com.example.sdksamples;

import com.impinj.octane.*;

import java.util.ArrayList;
import java.util.Scanner;

// 锁定用户内存
public class LockUserMemory {

    public static void main(String[] args) {

        try {
            String hostname = System.getProperty(SampleProperties.hostname);

            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + SampleProperties.hostname + "' property");
            }

            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setIncludeAntennaPortNumber(true);

            // Apply the new settings
            reader.applySettings(settings);

            // create the reader op sequence
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<TagOp>());
            // only execute this one time since it will fail the second time due
            // to password lock
            // 仅执行一次，因为密码锁定将导致第二次失败
            seq.setExecutionCount((short) 1);
            seq.setState(SequenceState.Active);
            seq.setId(1);

            // write a new access password
            TagWriteOp writeOp = new TagWriteOp();
            writeOp.setMemoryBank(MemoryBank.Reserved);
            // access password starts at an offset into reserved memory
            // 访问密码从保留内存的偏移量开始
            // 使用 执行密码 部分的存储体
            writeOp.setWordPointer(WordPointers.AccessPassword);
            writeOp.setData(TagData.fromHexString("abcd0123"));
            // no access password at this point
            // 此时没有访问密码

            TagLockOp lockOp = new TagLockOp();
            // lock the access password so it can't be changed
            // since we have a password set, we have to use it
            // 锁定访问密码，使其无法更改
            // 因为我们设置了密码，所以我们必须使用它
            lockOp.setAccessPassword(TagData.fromHexString("abcd0123"));
            lockOp.setAccessPasswordLockType(TagLockState.Lock);

            // uncomment to lock user memory so it can't be changed
            // 取消注释以锁定用户内存，因此无法更改
            // lockOp.setUserLockType(TagLockState.Lock);

            // add to the list
            seq.getOps().add(writeOp);
            seq.getOps().add(lockOp);

            // Use target tag to only apply to some EPCs
            // 使用目标标记仅适用于某些EPC
            String targetEpc = System.getProperty(SampleProperties.targetTag);

            if (targetEpc != null) {
                seq.setTargetTag(new TargetTag());
                seq.getTargetTag().setBitPointer(BitPointers.Epc);
                seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(targetEpc);
            } else {
                // or just send NULL to apply to all tags
                // 或仅发送NULL以应用于所有标签
                seq.setTargetTag(null);
            }

            // add to the reader. The reader supports multiple sequences
            // 添加到阅读器。 阅读器支持多种序列
            reader.addOpSequence(seq);

            // set up listeners to hear stuff back from SDK
            // reader.setTagReportListener(new TagReportListenerImplementation());
            reader.setTagOpCompleteListener(
                    new TagOpCompleteListenerImplementation());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
