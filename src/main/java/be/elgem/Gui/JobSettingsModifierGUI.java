package be.elgem.Gui;

import be.elgem.Jobs.Jobs.Job;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JobSettingsModifierGUI extends GUI{
    Job jobToModify;

    private boolean isChoosingAnItem = false;
    private boolean isInputing = false;
    private String inputDestination = "";

    public JobSettingsModifierGUI(Player player, Job jobToModify) {
        super(player, 9, ChatColor.RED + "Paramètres de " + jobToModify.getJobName());

        this.jobToModify = jobToModify;

        createInventory();
    }

    @Override
    protected void createInventory() {
        addItem(0, createItemStack("Changer l'icône du métier", jobToModify.getIcon()), this::activateItemSelection);
        addItem(1, createItemStack("Changer le nom du métier", Material.NAME_TAG), () -> activateChatInput("name"));
    }

    public boolean isChoosingAnItem() {
        return isChoosingAnItem;
    }

    public boolean isInputing() {
        return isInputing;
    }

    public void setNewIcon(Material newIcon) {
        jobToModify.setIcon(newIcon);

        addItem(0, createItemStack("Changer l'icône du métier", newIcon), this::activateItemSelection);

        isChoosingAnItem = false;
    }

    public void setNewName(String newName) {
        jobToModify.setJobName(newName);
    }

    public void sendInput(String message) {
        switch (inputDestination) {
            case "name":
                setNewName(message);
                break;
        }

        new JobSettingsModifierGUI(player, jobToModify).openInventory();
    }

    private void activateItemSelection() {
        isChoosingAnItem = true;
        player.closeInventory();
        player.sendMessage("[Jobs] Faites un clique droit avec l'objet que vous souhaitez définir comme icône.");
    }

    private void activateChatInput(String destination) {
        isInputing = true;
        inputDestination = destination;
        player.closeInventory();
        player.sendMessage("[Jobs] Entrez la nouvelle valeur dans le chat");
    }


}
