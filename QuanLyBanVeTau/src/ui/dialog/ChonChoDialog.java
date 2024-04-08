package ui.dialog;

import ui.component.Carriage;
import ui.component.Cabin;
import ui.component.Seat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonChoDialog extends JDialog {
    private Carriage carriage;
    private JComboBox<String> carriageTypeComboBox;
    private JComboBox<Integer> cabinNumberComboBox;
    private JPanel seatPanel;

    public ChonChoDialog(Frame parent) {
        super(parent, "Chọn Chỗ", true);
        initializeComponents();
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel chọn loại toa và khoang
        JPanel topPanel = new JPanel();
        JLabel carriageLabel = new JLabel("Chọn loại toa:");
        String[] carriageTypes = {"Toa 1 (50 chỗ)", "Toa 2 (50 chỗ)", "Toa 3 (8 khoang, 6 chỗ)",
                "Toa 4 (8 khoang, 6 chỗ)", "Toa 5 (8 khoang, 4 chỗ)", "Toa 6 (8 khoang, 4 chỗ)"};
        carriageTypeComboBox = new JComboBox<>(carriageTypes);
        carriageTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCabinComboBox();
                updateSeatPanel(); // Cập nhật panel chọn chỗ sau khi chọn loại toa
            }
        });
        topPanel.add(carriageLabel);
        topPanel.add(carriageTypeComboBox);

        JLabel cabinLabel = new JLabel("Chọn số khoang:");
        cabinNumberComboBox = new JComboBox<>();
        topPanel.add(cabinLabel);
        topPanel.add(cabinNumberComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel chọn chỗ
        seatPanel = new JPanel(); // Panel chứa các chỗ ngồi
        seatPanel.setLayout(new GridLayout(0, 4)); // GridLayout với số hàng là 0 để tự động điều chỉnh số hàng
        carriage = Carriage.createCarriageWith50Seats(); // Mặc định là toa 1
        updateSeatPanel();

        mainPanel.add(seatPanel, BorderLayout.CENTER);
        mainPanel.setPreferredSize(new Dimension(1000, 800)); // Đặt kích thước ưu tiên cho panel chọn chỗ
        add(mainPanel);
    }
    
 // Phương thức để xóa lựa chọn trên các chỗ ngồi
    private void clearSeatSelection() {
        Component[] components = seatPanel.getComponents();
        for (Component component : components) {
            if (component instanceof Seat) {
                ((Seat) component).clearSelection();
            }
        }
    }

    // Cập nhật panel chọn chỗ dựa trên loại toa và khoang đã chọn
    private void updateSeatPanel() {
    	int selectedCarriageIndex = carriageTypeComboBox.getSelectedIndex();
        Integer selectedCabinNumber = (Integer) cabinNumberComboBox.getSelectedItem();

        switch (selectedCarriageIndex) {
            case 0:
            case 1:
                carriage = Carriage.createCarriageWith50Seats();
                break;
            case 2:
            case 3:
                carriage = Carriage.createCarriageWith8Cabins6Seats();
                break;
            case 4:
            case 5:
                carriage = Carriage.createCarriageWith8Cabins4Seats();
                break;
            default:
                return;
        }
        seatPanel.removeAll(); // Xóa tất cả các ghế hiện tại trên panel
        clearSeatSelection(); // Xóa lựa chọn trên các ghế

        if (selectedCarriageIndex == 0 || selectedCarriageIndex == 1) {
            // Toa 1 và 2 chỉ có một khoang, nên hiển thị số chỗ của khoang đầu tiên
            addSeatsToPanel(carriage.getCabins().get(0).getSeats());
        } else {
            // Các toa khác có nhiều khoang, hiển thị số chỗ của khoang được chọn
            List<Cabin> cabins = carriage.getCabins();
            if (selectedCabinNumber != null && selectedCabinNumber > 0 && selectedCabinNumber <= cabins.size()) {
                addSeatsToPanel(cabins.get(selectedCabinNumber - 1).getSeats());
            } else {
                // Nếu không có số khoang nào được chọn hoặc số khoang không hợp lệ, hiển thị mặc định
                addSeatsToPanel(cabins.get(0).getSeats());
            }
        }

        // Cập nhật giao diện
        revalidate();
        repaint();
    }

    // Cập nhật ComboBox số khoang tương ứng với loại toa được chọn
 // Cập nhật ComboBox số khoang tương ứng với loại toa được chọn
    private void updateCabinComboBox() {
        int selectedCarriageIndex = carriageTypeComboBox.getSelectedIndex();
        int numCabins = 1; // Mặc định là một khoang
        switch (selectedCarriageIndex) {
            case 0:
            case 1:
                numCabins = 1; // Toa 1 và 2 chỉ có một khoang
                cabinNumberComboBox.setVisible(false); // Ẩn ComboBox số khoang
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                numCabins = 8; // Các toa khác có 8 khoang
                cabinNumberComboBox.setVisible(true); // Hiện ComboBox số khoang
                break;
        }

        cabinNumberComboBox.removeAllItems();
        for (int i = 1; i <= numCabins; i++) {
            cabinNumberComboBox.addItem(i);
        }
        
        // Xóa lựa chọn trên các chỗ ngồi
        clearSeatSelection();
    }


    // Thêm các chỗ ngồi vào panel
    private void addSeatsToPanel(List<Seat> seats) {
        for (Seat seat : seats) {
            seatPanel.add(seat);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {
            ChonChoDialog dialog = new ChonChoDialog(frame);
            dialog.setVisible(true);
        });
    }
}
