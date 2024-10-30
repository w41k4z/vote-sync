import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class ResultEditModal extends StatefulWidget {
  final int nullVotes;
  final int blankVotes;

  const ResultEditModal({
    super.key,
    required this.nullVotes,
    required this.blankVotes,
  });

  @override
  State<ResultEditModal> createState() => _ResultEditModalState();
}

class _ResultEditModalState extends State<ResultEditModal> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _nullVotesController;
  late TextEditingController _blankVotesController;
  late List<TextEditingController> _candidatesControllers;

  @override
  void initState() {
    super.initState();
    _nullVotesController =
        TextEditingController(text: widget.nullVotes.toString());
    _blankVotesController =
        TextEditingController(text: widget.blankVotes.toString());
    _candidatesControllers =
        List.generate(4, (index) => TextEditingController(text: '0'));
  }

  @override
  void dispose() {
    _nullVotesController.dispose();
    _blankVotesController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text(
                'Modifier',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              _buildNumberInputField(_nullVotesController, 'Nuls'),
              const SizedBox(height: 16),
              _buildNumberInputField(_blankVotesController, 'Blancs'),
              const SizedBox(height: 15),
              //
              const Divider(),
              //
              const SizedBox(height: 15),
              for (int i = 0; i < 4; i++) ...[
                _buildNumberInputField(
                    _candidatesControllers[i], 'Candidat N° ${i + 1}'),
                if (i != 3) const SizedBox(height: 16),
              ],
              const SizedBox(height: 24),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 8),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                  backgroundColor: AppColors.primaryGreen,
                ),
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    final nulls =
                        double.tryParse(_nullVotesController.text) ?? 0;
                    final blanks =
                        double.tryParse(_blankVotesController.text) ?? 0;
                    Navigator.pop(
                      context,
                      {
                        'nullVotes': nulls,
                        'blankVotes': blanks,
                        'candidates': _candidatesControllers
                            .map((e) => double.tryParse(e.text) ?? 0)
                            .toList()
                      },
                    );
                  }
                },
                child: const Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      'Enregistrer',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                      textAlign: TextAlign.center,
                    ),
                    SizedBox(width: 10), // Spacing between icon and text
                    Icon(Icons.save, size: 28, color: Colors.white),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildNumberInputField(
      TextEditingController controller, String label) {
    return TextFormField(
      controller: controller,
      keyboardType: const TextInputType.numberWithOptions(
        decimal: false,
        signed: true,
      ),
      decoration: InputDecoration(
        labelText: label,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
        ),
      ),
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Veillez entrer une valeur';
        }
        if (double.tryParse(value) == null) {
          return 'Entrez une valeur numérique valide';
        }
        return null;
      },
    );
  }
}
