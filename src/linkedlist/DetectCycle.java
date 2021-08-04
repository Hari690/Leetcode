package linkedlist;

/**
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next
 * pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed
 * as a parameter.
 *
 * Notice that you should not modify the linked list.
 */
public class DetectCycle {

    public ListNode detectCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast)
                break;
        }

        if (fast == null || fast.next == null)
            return null;

        slow = head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }
}